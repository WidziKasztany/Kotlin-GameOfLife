
# Kotlin-GameOfLife

Super optimized Single Threaded Game of Life made in Kotlin.

Here are some performance measurements:

| Iterations                                   | 1     | 100   | 100,000 | 1,000,000 |
|----------------------------------------------|-------|-------|---------|-----------|
| Execution time (ms) for 5 x 5 grid           | -     | -     | 12ms    | 120ms     |
| Execution time (ms) for 50 x 50 grid         | -     | -     | 800ms   | 8000ms    |
| Execution time (ms) for 1,000 x 1,000 grid   | -     | 250ms | -       | -         |
| Execution time (ms) for 10,000 x 10,000 grid | 250ms | -     | -       | -         |

Tests were ran on Intel i5-3570 3.40GHz with 8GB of 1333 MHz DDR3 RAM