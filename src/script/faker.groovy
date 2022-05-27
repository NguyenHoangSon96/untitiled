import com.github.javafaker.Faker

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.function.Supplier
import java.util.stream.Collectors

def faker = new Faker()
ExecutorService executorService = Executors.newWorkStealingPool();
Supplier s = () -> {
    println(Thread.currentThread().getName())
    def s = new HashSet()
    28000000.times {
        def contactName = faker.name().firstName() + faker.name().lastName()
        s.add(contactName)
    }
    return s.size()
}

List<CompletableFuture<Integer>> futureList = []
7.times { it ->
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(s)
    futureList.add(future)
}

List<Integer> l = futureList.stream().map(c -> c.join()).collect(Collectors.toList());
println(l)

