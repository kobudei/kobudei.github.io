#!/usr/bin/env python3
"""
Fetch public Meetup group data and write a local snapshot file consumed by the web app.

Output format is a simple key=value properties file to keep parsing lightweight in Kotlin.
"""

from __future__ import annotations

import argparse
import datetime as dt
import html
import json
import pathlib
import re
import sys
import urllib.error
import urllib.request


def collapse(value: str) -> str:
    return re.sub(r"\s+", " ", value).strip()


def find_next_data(document: str) -> dict:
    match = re.search(
        r'<script id="__NEXT_DATA__" type="application/json">(.*?)</script>',
        document,
        flags=re.DOTALL,
    )
    if not match:
        raise RuntimeError("Could not find __NEXT_DATA__ payload in Meetup HTML.")
    return json.loads(html.unescape(match.group(1)))


def extract_group_snapshot(next_data: dict, source_url: str) -> dict:
    apollo_state = next_data["props"]["pageProps"]["__APOLLO_STATE__"]
    root_query = apollo_state["ROOT_QUERY"]

    group_ref = None
    for key, value in root_query.items():
        if key.startswith("groupByUrlname:") and isinstance(value, dict) and "__ref" in value:
            group_ref = value["__ref"]
            break

    if not group_ref:
        raise RuntimeError("Could not resolve group reference in Apollo state.")

    group = apollo_state[group_ref]

    organizer_name = "Kobudei Team"
    organizer_ref = group.get("organizer", {}).get("__ref")
    if organizer_ref and organizer_ref in apollo_state:
        organizer_name = collapse(apollo_state[organizer_ref].get("name", organizer_name))

    members = ""
    stats = group.get("stats")
    if isinstance(stats, dict):
        member_counts = stats.get("memberCounts")
        if isinstance(member_counts, dict):
            member_value = member_counts.get("all")
            if member_value is not None:
                members = str(member_value)

    upcoming_events = ""
    for key, value in group.items():
        if key.startswith("events({") and '"ACTIVE"' in key and isinstance(value, dict):
            total_count = value.get("totalCount")
            if total_count is not None:
                upcoming_events = str(total_count)
            break

    topics: list[str] = []
    for topic_ref in group.get("activeTopics", []):
        if not isinstance(topic_ref, dict):
            continue
        ref = topic_ref.get("__ref")
        if not ref:
            continue
        topic = apollo_state.get(ref)
        if not isinstance(topic, dict):
            continue
        topic_name = collapse(topic.get("name", ""))
        if topic_name:
            topics.append(topic_name)

    description = collapse(group.get("description", ""))

    return {
        "name": collapse(group.get("name", "Kobudei")),
        "city": collapse(group.get("city", "Bucharest")),
        "country": collapse(group.get("country", "RO")).upper(),
        "timezone": collapse(group.get("timezone", "")),
        "organizer": organizer_name,
        "members": members,
        "upcomingEvents": upcoming_events,
        "foundedDate": collapse(group.get("foundedDate", "")),
        "topics": "|".join(topics),
        "description": description,
        "sourceUrl": source_url,
        "updatedAt": dt.datetime.now(dt.timezone.utc).replace(microsecond=0).isoformat(),
    }


def fetch_page(url: str, timeout_seconds: int) -> str:
    req = urllib.request.Request(
        url,
        headers={
            "User-Agent": (
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) "
                "AppleWebKit/537.36 (KHTML, like Gecko) "
                "Chrome/122.0.0.0 Safari/537.36"
            )
        },
    )
    with urllib.request.urlopen(req, timeout=timeout_seconds) as response:
        return response.read().decode("utf-8", errors="replace")


def write_properties(path: pathlib.Path, data: dict) -> None:
    ordered_keys = [
        "name",
        "city",
        "country",
        "timezone",
        "organizer",
        "members",
        "upcomingEvents",
        "foundedDate",
        "topics",
        "description",
        "sourceUrl",
        "updatedAt",
    ]

    lines = []
    for key in ordered_keys:
        value = str(data.get(key, ""))
        value = value.replace("\n", " ").replace("\r", " ").strip()
        lines.append(f"{key}={value}")

    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Fetch Meetup snapshot.")
    parser.add_argument(
        "--url",
        default="https://www.meetup.com/kobudei/",
        help="Meetup group URL.",
    )
    parser.add_argument(
        "--output",
        default="composeApp/src/webMain/resources/meetup-snapshot.properties",
        help="Path to output properties file.",
    )
    parser.add_argument(
        "--timeout",
        type=int,
        default=20,
        help="HTTP timeout in seconds.",
    )
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    output_path = pathlib.Path(args.output)

    try:
        html_doc = fetch_page(args.url, args.timeout)
        next_data = find_next_data(html_doc)
        snapshot = extract_group_snapshot(next_data, args.url)
        write_properties(output_path, snapshot)
        print(f"Wrote Meetup snapshot to {output_path}")
        return 0
    except (KeyError, RuntimeError, urllib.error.URLError, TimeoutError) as error:
        if output_path.exists():
            print(
                "Failed to refresh Meetup snapshot, keeping existing file. "
                f"Reason: {error}",
                file=sys.stderr,
            )
            return 0
        print(f"Failed to fetch Meetup snapshot: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
