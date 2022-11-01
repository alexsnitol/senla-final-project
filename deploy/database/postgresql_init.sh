echo Drop database if exist
psql -U postgres -W -c "DROP DATABASE IF EXISTS real_estate_market"
echo

echo Create database
psql -U postgres -W -c "CREATE DATABASE real_estate_market ENCODING = 'UTF8'"
echo

echo Insert data in database
psql -U postgres -W -d "real_estate_market" -f "./postgresql_init.sql"
echo

echo Database setup is complete