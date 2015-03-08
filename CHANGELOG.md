# Change log

## [Unreleased] - [unreleased]
### Added:
- Webservice: Added endpoint to get building metadata for a given building type at `/v1/metadata/building/{type}`.
- Configuration: Added the config option `speedUpEverything`. When set to `true`, every building / research / ship takes 1 round to complete, needs no resources and all prerequisites are disabled.
- Webservice: Added query parameter `max` to limit the maximum number of events which are returned from `/v1/event`.
- JVM client: Added long-polling websocket method to get the round.
- Added endpoint to fetch metadata for single ship type, `/v1/metadata/ship/{type}`
- Added endpoint to fetch metadata for single technology type, `/v1/metadata/technology/{type}`
- Added endpoints to fetch building metadata for a level range, `/v1/metadata/building/range` and `/v1/metadata/building/{type}/range`
- Added endpoints to fetch technology metadata for a level range, `/v1/metadata/technology/range` and `/v1/metadata/technology/{type}/range`
- Added new debug configuration options which allow fine tuning debug mechanics like free ships, instant flights and more.

### Fixed:
- Webservice: Fixed a bug where the building metadata and technology metadata endpoint responded with 500 Server Error when passing a level smaller than 1.
- Webservice: Fixed a bug where a newly registered player didn't get access (#84)

### Changed
- Gameplay: The command center now harvests a minimal amount of resources to prevent "disabling" of planets.
- Webservice: Improved telescope scan results. Now empty planets are included in the response, the `owner` field is `null` in that case.
- Build: Only include scope runtime when building JAR with all dependencies. This reduces the size of the assembly.
- Build: JAR with all dependencies is now built on package phase instead of install phase.
- Webservice: Moved the websocket round endpoint from `/websocket/round` to `/v1/websocket/round`.
- Webservice: List all prerequisites for ships and technologies, even the implicit ones.
- Webservice: Events are now ordered descending by round

### Removed
- Removed configuration option `speedUpEverything`. Take a look at the new debug configuration options.

## [0.1.0] - 2015-02-21
### Fixed
- Started writing a changelog.