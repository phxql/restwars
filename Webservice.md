# Player

`GET` `/v1/me`

Returns the current logged in player as well as all planets of the player

`GET` `/v1/planet/{location}`

Returns information about the planet at the given location. If the current player doesn't own
the planet at the location, `403 Forbidden` is returned.
