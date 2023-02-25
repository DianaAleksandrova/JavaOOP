package calculator;

import java.util.Stack;

public class MemoryRecallOperation implements Operation{
    private Stack<Integer> memory;

    public MemoryRecallOperation(Stack<Integer> memory) {
        this.memory = memory;
    }

    @Override
    public void addOperand(int operand) {

    }

    @Override
    public int getResult() {
        return this.memory.pop();
    }

    @Override
    public boolean isCompleted() {
        return !this.memory.isEmpty();
    }
}
