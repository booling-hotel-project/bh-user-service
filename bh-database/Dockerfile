FROM liquibase/liquibase:4.18

COPY bh-database/src/main/resources/db/changelog /liquibase/changelog

ENTRYPOINT ["liquibase"]
CMD ["--changeLogFile=changelog/db.changelog-master.yaml", "update"]