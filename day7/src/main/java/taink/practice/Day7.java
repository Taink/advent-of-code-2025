package taink.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day7 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
        List<String> input;
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TachyonManifold tachyonManifold = new TachyonManifold(input);
        do {
            tachyonManifold.propagateBeam();
            IO.println("Current state of the manifold:");
            IO.println(tachyonManifold);
            IO.println();
        } while(!tachyonManifold.isFullyPropagated());

        IO.println("Split count: "+tachyonManifold.getSplitCount());
    }
}
