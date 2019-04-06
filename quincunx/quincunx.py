#!/usr/bin/env python3
"""
The *bean machine*, also known as the *Galton Board* or *quincunx*, is a device invented by Sir Francis Galton to
demonstrate the central limit theorem, in particular that the normal distribution is approximate to the binomial distribution.
"""

import argparse
import random
import threading


class Board:
    """
    Class Board
    
    Contains multiple bins that collect beans
    Contains multiple levels of pegs
    """

    def __init__(self, bins: int):
        """Make a new board of the specified size"""
        self.bins = [0] * bins
        self.levels = bins-1 # todo: ask about: self._pegs = bins // 2

    def get_num_levels(self):
        return self.levels

    def get_num_beans(self):
        return sum(self.bins)

    def __len__(self):
        """Return the board size"""
        return len(self.bins)

    def __getitem__(self, idx: int):
        """Get number of beans in the specified bin"""
        return self.bins[idx]

    def __setitem__(self, idx: int, new_value: int):
        """Set number of beans in the specified bin"""
        self.bins[idx] = new_value

    def __str__(self): # todo: ask about pos:int as an argument
        """Print status"""
        bin_status = "|"
        for bin in self.bins:
            bin_status = bin_status + " {:^5}|".format(bin)
        bin_status = bin_status + "{:10}".format(self.get_num_beans())
        return bin_status

    @property
    def pegs(self):
        """Return number of peg levels"""
        return self.levels


class Bean(threading.Thread):
    """
    Class Bean
    Data members: board, current position, probability
    """

    def __init__(self, board: object, bin_num: int, prob: float, name): # todo: ask about lock: object as argument):
        super().__init__(name=name)
        """Make a new Bean"""
        self.board = board
        self.bin_num = bin_num
        self.prob = prob

    def move_left(self, current_peg_position):
        """Move a bean left"""
        # Add bean to new bin
        self.board[current_peg_position] += 1
        # Subtract bean from old bin
        self.board[self.bin_num] -= 1
        # Reassign bean's location to the new bin
        self.bin_num = current_peg_position

    def move_right(self, current_peg_position):
        """Move a bean right"""
        # Add bean to new bin
        self.board[current_peg_position+1] += 1
        # Subtract bean from old bin
        self.board[self.bin_num] -= 1
        # Reassign bean's location to the new bin
        self.bin_num = current_peg_position + 1

    def run(self):
        """Run a bean through the pegs"""
        # Sanity check
        # print("{} started!".format(self.getName()))
        current_peg_position = 0
        for i in range(self.board.get_num_levels()-1):
            if random.random() > self.prob:
                current_peg_position += 1

        # We are now at the last level, we must place the bean in its appropriate bin
        if random.random() > self.prob:
            self.move_right(current_peg_position)
        else:
            self.move_left(current_peg_position)
        # Sanity check
        # print("{} finished!".format(self.getName()))

    def __str__(self):
        return f"Bean(current_position:{self.current_position})"


def main():
    """Main function"""
    # Parse command-line arguments
    parser = argparse.ArgumentParser(description="Process the arguments.")
    parser.add_argument("--beans", type=int, help="Number of beans")
    parser.add_argument("--bins", type=int, help="Number of bins")
    parser.add_argument("--start", type=int, help="Starting bin number")
    parser.add_argument("--prob", type=float, help="Probability of a bean moving left or right")
    args = parser.parse_args()

    print(f"Beans: {args.beans}, bins: {args.bins}, start: {args.start}, prob: {args.prob}")
    print("Start")

    # Create a board
    galton_board = Board(args.bins)

    # Place all the beans at the starting index
    galton_board[args.start] = args.beans

    # Create a list of jobs
    jobs = []

    # Create jobs (beans)
    for i in range(args.beans):
        bean_thread = Bean(galton_board, args.start, args.prob, name="Thread-{}".format(i + 1))
        jobs.append(bean_thread)

    # Print the board status
    print(galton_board)

    # Start jobs
    for job in jobs:
        job.start()

    # Stop jobs
    for job in jobs:
        job.join()

    # Print the board status
    print(galton_board)

    print("Done")


if __name__ == "__main__":
    main()

