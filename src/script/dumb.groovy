import groovy.sql.Sql

def sql = Sql.newInstance('jdbc:postgresql://localhost:5432/el', null, null, 'org.postgresql.Driver')

sql.execute("TRUNCATE words RESTART IDENTITY")
2000.times { it ->
    {
        println(it)
        def status = it % 2 == 0 ? true : false
        sql.executeInsert("INSERT INTO words(code, noun, verb, adjective, adverb, definition, status) " +
                "VALUES ('code${it}', 'noun${it}', 'verb${it}', 'adjective${it}', 'adverb${it}', 'definition${it}', ${status})")
    }
}

sql.close()
