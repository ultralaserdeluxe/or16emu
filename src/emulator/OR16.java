package emulator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander on 2014-10-07.
 */
public class OR16 implements CPU {
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

    public OR16(Memory memory) {
        this.memory = memory;

        observers = new ArrayList<Object>();
    }

    @Override
    public void tick() {
        if (halted) return;

        // Fetch
        ip = memory.read(pc++);

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
            case 11:
                inc();
                break;
            case 12:
                dec();
                break;
            case 13:
                inx();
                break;
            case 14:
                dex();
                break;
            case 15:
                ins();
                break;
            case 16:
                des();
                break;
            case 17:
                lsl();
                break;
            case 18:
                lsr();
                break;
            case 19:
                asr();
                break;
            case 20:
                asl();
                break;
            case 21:
                and();
                break;
            case 22:
                or();
                break;
            case 23:
                xor();
                break;
            case 24:
                jmp();
                break;
            case 25:
                jmpn();
                break;
            case 26:
                jmpz();
                break;
            case 27:
                jsr();
                break;
            case 28:
                rts();
                break;
            case 29:
                cmp();
                break;
            case 31:
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
        pc = ip = acc = sp = xr = sr = ticks = 0;
        halted = false;
        notifyObservers();
    }

    @Override
    public boolean isHalted() {
        return halted;
    }

    private void update_sr() {
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
        acc = fetch_operand(ip);
        update_sr();
    }

    private void sta() {
        System.out.println("STA");
        memory.write(fetch_operand(ip), acc);
    }

    private void ldx() {
        System.out.println("LDX");
        xr = fetch_operand(ip);
    }

    private void stx() {
        System.out.println("STX");
        memory.write(fetch_operand(ip), xr);
    }

    private void lds() {
        System.out.println("LDS");
        sp = fetch_operand(ip);
    }

    private void sts() {
        System.out.println("STS");
        memory.write(fetch_operand(ip), sp);
    }

    private void push() {
        System.out.println("PUSH");
        memory.write(sp++, acc);
    }

    private void pull() {
        System.out.println("PULL");
        acc = memory.read(--sp);
        update_sr();
    }

    private void add() {
        System.out.println("ADD");
        acc += fetch_operand(ip);
        update_sr();
    }

    private void sub() {
        System.out.println("SUB");
        acc -= fetch_operand(ip);
        update_sr();
    }

    private void inc() {
        System.out.println("INC");
        acc++;
        update_sr();
    }

    private void dec() {
        System.out.println("DEC");
        acc--;
        update_sr();
    }

    private void inx() {
        System.out.println("INX");
        xr++;
    }

    private void dex() {
        System.out.println("DEX");
        xr--;
    }

    private void ins() {
        System.out.println("INS");
        sp++;
    }

    private void des() {
        System.out.println("DES");
        sp--;
    }

    private void lsl() {
        System.out.println("LSL");
        acc <<= fetch_operand(ip);
        update_sr();
    }

    private void lsr() {
        System.out.println("LSR");
        acc >>>= fetch_operand(ip);
        update_sr();
    }

    private void asr() {
        System.out.println("ASR");
        acc >>= fetch_operand(ip);
        update_sr();
    }

    private void asl() {
        System.out.println("ASL (not implemented)");
        acc <<= fetch_operand(ip);
        update_sr();
    }

    private void and() {
        System.out.println("AND");
        acc &= fetch_operand(ip);
        update_sr();
    }

    private void or() {
        System.out.println("OR");
        acc |= fetch_operand(ip);
        update_sr();
    }

    private void xor() {
        System.out.println("XOR");
        acc ^= fetch_operand(ip);
        update_sr();
    }

    private void jmp() {
        System.out.println("JMP");
        pc = fetch_operand(ip);
    }

    private void jmpn() {
        System.out.println("JMPN");
        int address = fetch_operand(ip);
        if (acc < 0) pc = address;
    }

    private void jmpz() {
        System.out.println("JMPZ");
        int address = fetch_operand(ip);
        if (acc < 0) pc = address;
    }

    private void jsr() {
        System.out.println("JSR");
        memory.write(sp++, pc);
        pc = fetch_operand(ip);
    }

    private void rts() {
        System.out.println("RTS");
        pc = memory.read(--sp);
    }

    private void cmp() {
        System.out.println("CMP");
        int old_acc = acc;
        acc -= fetch_operand(ip);
        update_sr();
        acc = old_acc;
    }

    private void halt() {
        System.out.println("HALT");
        halted = true;
    }

    private int fetch_operand(int ip) {
        int operand = 0;
        int address;

        switch (ip & 0x7) {
            // Absolute addressing
            case 0x0:
                address = memory.read(pc++);
                operand = memory.read(address);
                break;
            // Indirect addressing
            case 0x1:
                address = memory.read(pc++);
                operand = memory.read(memory.read(address));
                break;
            // Indexed addressing
            case 0x2:
                address = memory.read(pc++) + xr;
                operand = memory.read(address);
                break;
            // Relative addressing
            case 0x3:
                address = pc + 2 + memory.read(pc++);
                operand = memory.read(address);
                break;
            // Immediate addressing
            case 0x4:
                operand = memory.read(pc++);
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
            ((IObserver) o).hasChanged();
        }
    }
}
