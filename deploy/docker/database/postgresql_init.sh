#!/bin/bash

psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f "/var/lib/postgresql/real_estate_market_init.sql"