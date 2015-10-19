package emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander on 2014-10-07.
 */
public class OR16 implements Processor
{
    private final List<Object> observers;
    private final Memory memory;

    // Registers
    private int pc = 0;
    private int ip = 0;
    private int acc = 0;
    private int sp = 0;
    private int xr = 0;
    private int sr = 0;

    // Emulation state
    private boolean halted = false;
    private int ticks = 0;

    // Others
    private static final int REGISTER_MASK = 0xFFFF;

    public OR16(Memory memory) {
        this.memory = memory;

        observers = new ArrayList<Object>();
    }

    @Override
    public void tick() {
        // TODO: Put all this in a map (opcode -> function). Index into map with opcode and call function. Voila.
        final int incOp = 11;
        final int decOp = 12;
        final int inxOp = 13;
        final int dexOp = 14;
        final int insOp = 15;
        final int desOp = 16;
        final int lslOp = 17;
        final int lsrOp = 18;
        final int asrOp = 19;
        final int aslOp = 20;
        final int andOp = 21;
        final int orOp = 22;
        final int xorOp = 23;
        final int jmpOp = 24;
        final int jmpnOp = 25;
        final int jmpzOp = 26;
        final int jsrOp = 27;
        final int rtsOp = 28;
        final int cmpOp = 29;
        final int haltOp = 31;

        if (halted) return;

        // Fetch
        ip = memory.read(pc);
        pc++;

        // Execute
        switch (ip >> 3) {
            case 0:
                nop();
                break;
            case 1:
                lda();
                break;
            case 2:
                sta();
                break;
            case 3:
                ldx();
                break;
            case 4:
                stx();
                break;
            case 5:
                lds();
                break;
            case 6:
                sts();
                break;
            case 7:
                push();
                break;
            case 8:
                pull();
                break;
            case 9:
                add();
                break;
            case 10:
                sub();
                break;
            case incOp:
                inc();
                break;
            case decOp:
                dec();
                break;
            case inxOp:
                inx();
                break;
            case dexOp:
                dex();
                break;
            case insOp:
                ins();
                break;
            case desOp:
                des();
                break;
            case lslOp:
                lsl();
                break;
            case lsrOp:
                lsr();
                break;
            case asrOp:
                asr();
                break;
            case aslOp:
                asl();
                break;
            case andOp:
                and();
                break;
            case orOp:
                or();
                break;
            case xorOp:
                xor();
                break;
            case jmpOp:
                jmp();
                break;
            case jmpnOp:
                jmpn();
                break;
            case jmpzOp:
                jmpz();
                break;
            case jsrOp:
                jsr();
                break;
            case rtsOp:
                rts();
                break;
            case cmpOp:
                cmp();
                break;
            case haltOp:
                halt();
                break;
            default:
                System.out.println("INVALID");
                halted = true;
                break;
        }
        ticks++;
        notifyObservers();
    }

    @Override
    public void reset() {
        pc = 0;
        ip = 0;
        acc = 0;
        sp = 0;
        xr = 0;
        sr = 0;
        ticks = 0;
        halted = false;
        notifyObservers();
    }

    @Override
    public boolean isHalted() {
        return halted;
    }

    private void updateSr() {
        if (acc < 0) {
            sr |= 1;
        } else if (acc == 0) {
            sr |= 2;
        } else {
            sr = 0;
        }
    }

    @Override
    public HashMap<String, String> getCPUState() {
        HashMap<String, String> state = new HashMap<String, String>();

        state.put("TICKS", Integer.toString(ticks));
        state.put("PC", Integer.toString(pc));
        state.put("IP", Integer.toString(ip));
        state.put("SP", Integer.toString(sp));
        state.put("XR", Integer.toString(xr));
        state.put("A", Integer.toString(acc));
        state.put("SR", Integer.toString(sr));

        return state;
    }

    private void nop() {
        System.out.println("NOP");
    }

    private void lda() {
        System.out.println("LDA");
        acc = fetchOperand(ip);
        updateSr();
    }

    private void sta() {
        System.out.println("STA");
        memory.write(fetchOperand(ip), acc);
    }

    private void ldx() {
        System.out.println("LDX");
        xr = fetchOperand(ip);
    }

    private void stx() {
        System.out.println("STX");
        memory.write(fetchOperand(ip), xr);
    }

    private void lds() {
        System.out.println("LDS");
        sp = fetchOperand(ip);
    }

    private void sts() {
        System.out.println("STS");
        memory.write(fetchOperand(ip), sp);
    }

    private void push() {
        System.out.println("PUSH");
        memory.write(sp, acc);
        sp++;
    }

    private void pull() {
        System.out.println("PULL");
        --sp;
        acc = memory.read(sp);
        updateSr();
    }

    private void add() {
        System.out.println("ADD");
        acc = (acc + fetchOperand(ip)) & REGISTER_MASK;
        updateSr();
    }

    private void sub() {
        System.out.println("SUB");
        acc -= fetchOperand(ip);
        acc = (acc - fetchOperand(ip)) & REGISTER_MASK;
        updateSr();
    }

    private void inc() {
        System.out.println("INC");
        acc = (acc + 1) & REGISTER_MASK;
        updateSr();
    }

    private void dec() {
        System.out.println("DEC");
        acc = (acc - 1) & REGISTER_MASK;
        updateSr();
    }

    private void inx() {
        System.out.println("INX");
        xr = (xr + 1) & REGISTER_MASK;
    }

    private void dex() {
        System.out.println("DEX");
        xr = (xr - 1) & REGISTER_MASK;
    }

    private void ins() {
        System.out.println("INS");
        sp = (sp + 1) & REGISTER_MASK;
    }

    private void des() {
        System.out.println("DES");
        sp = (sp - 1) & REGISTER_MASK;
    }

    private void lsl() {
        System.out.println("LSL");
        acc = (acc << fetchOperand(ip)) & REGISTER_MASK;
        updateSr();
    }

    private void lsr() {
        System.out.println("LSR");
        acc = (acc >>> fetchOperand(ip)) & REGISTER_MASK;
        updateSr();
    }

    private void asr() {
        System.out.println("ASR");
        acc = (acc >> fetchOperand(ip)) & REGISTER_MASK;
        updateSr();
    }

    private void asl() {
        System.out.println("ASL (not implemented)");
        lsl();
    }

    private void and() {
        System.out.println("AND");
        acc &= fetchOperand(ip);
        updateSr();
    }

    private void or() {
        System.out.println("OR");
        acc = (acc | fetchOperand(ip)) & REGISTER_MASK;
        updateSr();
    }

    private void xor() {
        System.out.println("XOR");
        acc ^= fetchOperand(ip);
        updateSr();
    }

    private void jmp() {
        System.out.println("JMP");
        pc = fetchOperand(ip);
    }

    private void jmpn() {
        System.out.println("JMPN");
        int address = fetchOperand(ip);
        if (acc < 0) pc = address;
    }

    private void jmpz() {
        System.out.println("JMPZ");
        int address = fetchOperand(ip);
        if (acc < 0) pc = address;
    }

    private void jsr() {
        System.out.println("JSR");
        memory.write(sp, pc);
        sp = (sp + 1) & REGISTER_MASK;
        pc = fetchOperand(ip);
    }

    private void rts() {
        System.out.println("RTS");
        --sp;
        pc = memory.read(sp);
    }

    private void cmp() {
        System.out.println("CMP");
        int oldAcc = acc;
        acc = (acc - fetchOperand(ip)) & REGISTER_MASK;
        updateSr();
        acc = oldAcc;
    }

    private void halt() {
        System.out.println("HALT");
        halted = true;
    }

    private int fetchOperand(int ip) {
        int operand = 0;
        int address;

        switch (ip & 0x7) {
            // Absolute addressing
            case 0x0:
                address = memory.read(pc);
                pc++;
                operand = memory.read(address);
                break;
            // Indirect addressing
            case 0x1:
                address = memory.read(pc);
                pc++;
                operand = memory.read(memory.read(address));
                break;
            // Indexed addressing
            case 0x2:
                address = memory.read(pc) + xr;
                pc++;
                operand = memory.read(address);
                break;
            // Relative addressing
            case 0x3:
                address = pc + 2 + memory.read(pc);
                pc++;
                operand = memory.read(address);
                break;
            // Immediate addressing
            case 0x4:
                operand = memory.read(pc);
                pc++;
                break;
            // No addressing
            case 0x5:
                break;
            default:
                System.out.println("Invalid addressing");
                halted = true;
                break;
        }

        return operand;
    }

    @Override
    public void addObserver(Object o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (Object o : observers) {
            ((IObserver) o).notifyObserver();
        }
    }
}
