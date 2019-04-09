# Quincunx

Simulate the Galton board using threading.

## Requirements

1. The board should have the specified size (11 by default).
1. There are should be some number of beans (1000 by default), each represented by a thread.
1. Initial state of the board: all particles in the middle bin of the board.
1. At every step a particle can move left or right with equal probability.
1. Each bin bounces off the finite number of pegs (5 levels of pegs by default).
1. The program must take parameters as command-line arguments and parse them

```bash
python3 galton.py --beans 1000 --bins 11 --start 5 --prob 0.5
Beans: 1000, bins: 11, start: 5, prob: 0.5
Start
|   0  |   0  |   0  |   0  |   0  | 1000 |   0  |   0  |   0  |   0  |   0  |      1000
|   1  |   9  |  43  |  127 |  214 |  255 |  213 |  97  |  33  |   8  |   0  |      1000
Done
```

## Running the program
Type the following line into a terminal to run the program:
```bash
python3 quincunx.py --beans 1000 --bins 11 --start 5 --prob 0.5
```

## References

* [Bean machine - Wikipedia](https://en.wikipedia.org/wiki/Bean_machine)
* [Thread (Java Platform SE 8 )](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)
* [Lesson: Concurrency (The Java™ Tutorials > Essential Classes)](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
* [threading — Thread-based parallelism — Python 3.7.3 documentation](https://docs.python.org/3/library/threading.html)
* [threading — Manage Concurrent Operations Within a Process — PyMOTW 3](https://pymotw.com/3/threading/)