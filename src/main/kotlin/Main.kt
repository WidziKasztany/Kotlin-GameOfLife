// Contains entire map of game of life.
//
// With Map#runTick it can write results of 1 tick of simulation to another map,
// instead of copying (performance reasons, reallocating large arrays takes longer than just swapping them)
//
// Arrays are 1 dimensional for performance boost
class Map(val sizex: Int, val sizey: Int) {
    // map[x + y * sizex] = cell
    private var map = ByteArray(sizex * sizey) {0};

    public fun runTick(dst: Map) { // dst = Destination map (where to write)
        // Calculations are split into 2 main steps for better performance
        // - Center
        // - Borders + Corners

        // Center Stage
        runCenterStage(dst);

        // Borders Stage
        runBordersStage(dst);
    }

    private fun runCenterStage(dst: Map) {
        var livingNeighbours = 0; // Allocate variable only once instead of doing it every time in for loop
        for(y in 1..< sizey - 1) {
            for(x in 1..< sizex - 1) {

                livingNeighbours =
                            this.map[(x - 1) + y * sizex] +
                            this.map[(x + 1) + y * sizex] +
                            this.map[x + (y - 1) * sizex] +
                            this.map[x + (y + 1) * sizex] +
                            this.map[(x + 1) + (y + 1) * sizex] +
                            this.map[(x + 1) + (y - 1) * sizex] +
                            this.map[(x - 1) + (y - 1) * sizex] +
                            this.map[(x - 1) + (y + 1) * sizex];

                // Usage of if expression rather than statement
                // Statements take a lot longer to run than expression
                dst.map[x + y * sizex] = if (this.map[x + y * sizex] == 1.toByte()) (
                        if (livingNeighbours < 2) 0.toByte() else
                            if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                        ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();

            }
        }
    }

    private fun runBordersStage(dst: Map) {
        var livingNeighbours = 0; // Allocate variable only once instead of doing it every time in for loop

        // X-Axis borders
        val bottomOffset = sizex * (sizey - 1);
        for(x in 1..< sizex - 1) {

            livingNeighbours =
                this.map[x - 1] +
                        this.map[x + 1] +
                        this.map[x + sizex] +
                        this.map[x + 1 + sizex] +
                        this.map[x - 1 + sizex];

            dst.map[x] = if (this.map[x] == 1.toByte()) (
                    if (livingNeighbours < 2) 0.toByte() else
                        if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                    ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();

            livingNeighbours =
                this.map[bottomOffset + x - 1] +
                        this.map[bottomOffset + x + 1] +
                        this.map[bottomOffset + x - sizex] +
                        this.map[bottomOffset + x + 1 - sizex] +
                        this.map[bottomOffset + x - 1 - sizex];

            dst.map[bottomOffset + x] = if (this.map[bottomOffset + x] == 1.toByte()) (
                    if (livingNeighbours < 2) 0.toByte() else
                        if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                    ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();

        }

        // Y-Axis borders
        val toRightOffset = sizex - 1;
        for(y in 1..< sizey - 1) {

            livingNeighbours =
                this.map[(y - 1) * sizex] +
                        this.map[(y + 1) * sizex] +
                        this.map[y * sizex + 1] +
                        this.map[(y - 1) * sizex + 1] +
                        this.map[(y + 1) * sizex + 1];

            dst.map[y * sizex] = if (this.map[y * sizex] == 1.toByte()) (
                    if (livingNeighbours < 2) 0.toByte() else
                        if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                    ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();

            livingNeighbours =
                this.map[(y - 1) * sizex + toRightOffset] +
                        this.map[(y + 1) * sizex + toRightOffset] +
                        this.map[y * sizex - 1 + toRightOffset] +
                        this.map[(y - 1) * sizex - 1 + toRightOffset] +
                        this.map[(y + 1) * sizex - 1 + toRightOffset];

            dst.map[y * sizex + toRightOffset] = if (this.map[y * sizex + toRightOffset] == 1.toByte()) (
                    if (livingNeighbours < 2) 0.toByte() else
                        if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                    ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();
        }

        // Top left corner
        livingNeighbours =
            this.map[1] +
                    this.map[0 + sizex] +
                    this.map[1 + sizex];

        dst.map[0] = if (this.map[0] == 1.toByte()) (
                if (livingNeighbours < 2) 0.toByte() else
                    if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();

        // Top right corner
        livingNeighbours =
            this.map[sizex - 2] +
                    this.map[sizex - 2 + sizex] +
                    this.map[sizex - 1 + sizex];

        dst.map[sizex - 1] = if (this.map[sizex - 1] == 1.toByte()) (
                if (livingNeighbours < 2) 0.toByte() else
                    if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();


        // Bottom left corner
        livingNeighbours =
            this.map[(sizey - 1) * sizex + 1] +
                    this.map[(sizey - 2) * sizex + 1] +
                    this.map[(sizey - 2) * sizex];

        dst.map[(sizey - 1) * sizex] = if (this.map[(sizey - 1) * sizex] == 1.toByte()) (
                if (livingNeighbours < 2) 0.toByte() else
                    if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();

        // Bottom right corner
        livingNeighbours =
            this.map[sizex * sizey - 2] +
                    this.map[sizex * sizey - 1 - sizex] +
                    this.map[sizex * sizey - 2 - sizex];

        dst.map[sizex * sizey - 1] = if (this.map[sizex * sizey - 1] == 1.toByte()) (
                if (livingNeighbours < 2) 0.toByte() else
                    if (livingNeighbours > 3) 0.toByte() else 1.toByte()
                ) else if (livingNeighbours == 3) 1.toByte() else 0.toByte();
    }

    public fun fillRandom() {
        map = ByteArray(sizex * sizey) { (0..1).random().toByte() };
    }
    public fun fill(state: Boolean) {
        if(state) {
            map = ByteArray(sizex * sizey) { 1 };
        }
        else {
            map = ByteArray(sizex * sizey) { 0 };
        }
    }
    public fun set(x: Int, y: Int, state: Boolean) {
        map[x + y * sizex] = if(state) 1 else 0;
    }
    public fun get(x: Int, y: Int, state: Boolean) : Boolean {
        return map[x + y * sizex] == 1.toByte();
    }
    public fun print() {
        println("Simulation:")
        for(y in 0..< sizey) {
            for(x in 0..< sizex) {
                if(map[x + y * sizex] == 0.toByte()) {
                    print(". ");
                }
                else {
                    print("# ");
                }
            }
            println();
        }
    }
}

class GameOfLife(val sizex: Int, val sizey: Int) {
    // Map of actual cells
    private var source : Map = Map(sizex, sizey);

    // Map of new calculated cells
    private var working : Map = Map(sizex, sizey);

    // Swap working map with source map
    private fun swapMaps() {
        val map = source;
        source = working;
        working = map;
    }

    // Run one step of simulation
    public fun runTick() {
        source.runTick(working);
        swapMaps();
    }

    // Returns actual simulation map
    public fun getMap() : Map {
        return source;
    }
}

fun main(args: Array<String>) {
    val game = GameOfLife(5, 5);
    game.getMap().fill(false);
    game.getMap().set(1, 2, true);
    game.getMap().set(2, 2, true);
    game.getMap().set(3, 2, true);

    while(true) {
        game.getMap().print();
        val startTime = System.currentTimeMillis()
        for(i in 0..<1) {
            game.runTick();
        }
        val endTime = System.currentTimeMillis()
        println("Execution time: " + (endTime - startTime) + "ms");
        println("Press ENTER to Continue with simulation");
        readln();
    }
}