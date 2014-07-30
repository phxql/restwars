# RESTwars
An online game (like the good old browsergames) which can be played via a REST interface.

[![Build Status](https://travis-ci.org/phxql/restwars.svg?branch=master)](https://travis-ci.org/phxql/restwars)

## Universe
The game takes place in a universe. The universe consists of galaxies which consists of solar systems which consists of planets.

Each planet is identified with a set of coordinates: [g].[s].[p] where [g] is the number of the galaxy, [s] is the number of the solar system inside the galaxy and [p] is the number of the planet inside the solar system.

## New players 
A new player start with one planet in a randomized location in the universe. At the beginning, the only building on the planet is the command station.

## Resources
### Crystal
Crystal is needed for every building, ship and research. Crystals can be stolen from attackers.

### Gas
Gas is needed for every advanced technology. Gas can be stolen from attackers.

### Energy
Energy is needed for every building, ship and research. Energy is also consumed when traveling with ships through the universe. Energy can't be stolen from attackers.

## Buildings
### Command center
Allows the construction of other buildings. Upgrading the command center speeds up the construction of other buildings as well as increasing the storage capacity for all resources.

### Crystal mine
Generates crystals. Upgrading the mine increases up the generation rate.

### Gas refinery
Generates gas. Upgrading the refinery increases the generation rate.

### Solar panels
Generates energy. Upgrading the panels increases the generation rate.

### Shipyard
Allows the construction of ships. Upgrading the shipyard speeds up the construction of ships.

### Research center
Enables research. Upgrading the research center speeds up the research.

## Ships

## Research
