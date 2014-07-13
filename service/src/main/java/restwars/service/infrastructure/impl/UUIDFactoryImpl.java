package restwars.service.infrastructure.impl;

import restwars.service.infrastructure.UUIDFactory;

import java.util.UUID;

public class UUIDFactoryImpl implements UUIDFactory {
    @Override
    public UUID create() {
        return UUID.randomUUID();
    }
}
