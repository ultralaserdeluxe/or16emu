/**
 * Created by kebabdjuret on 2014-10-07.
 */
public class OR16 implements CPU {
    Memory memory;

    // Registers
    private int pc = 0;
    private int ip = 0;
    private int acc = 0;
    private int sp = 0;
    private int xr = 0;
    private int sr = 0;

    // Emulation state
    private boolean executing = true;

    public OR16(Memory memory) {
        this.memory = memory;
        this.memory.write(0, 12); // LDA #16
        this.memory.write(1, 16);
        this.memory.write(2, 20); // STA #9
        this.memory.write(3, 9);
        this.memory.write(4, 20); // STA #15
        this.memory.write(5, 15);
        this.memory.write(6, 248); // HALT
    }

    @Override
    public void tick() {
        if (!executing) return;

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
                executing = false;
                break;
        }
    }

    @Override
    public void reset() {
        pc = ip = acc = sp = xr = sr = 0;
        executing = true;
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

    public void print_registers() {
        System.out.format("PC = %d IP = %d SP = %d XR = %d SR = %d A = %d\n", pc, ip, sp, xr, sr, acc);

    }

    private void nop() {
        System.out.println("NOP");
    }

    private void lda() {
        System.out.println("LDA");
        acc = fetch_operand(ip);
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
    }

    private void lsr() {
        System.out.println("LSR");
        acc >>>= fetch_operand(ip);
    }

    private void asr() {
        System.out.println("ASR");
        acc >>= fetch_operand(ip);
    }

    private void asl() {
        System.out.println("ASL (not implemented)");
        acc <<= fetch_operand(ip);
    }

    private void and() {
        System.out.println("AND");
        acc &= fetch_operand(ip);
    }

    private void or() {
        System.out.println("OR");
        acc |= fetch_operand(ip);
    }

    private void xor() {
        System.out.println("XOR");
        acc ^= fetch_operand(ip);
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
        executing = false;
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
                executing = false;
                break;
        }

        return operand;
    }

    public static void main(String[] args) {
        // Create some components.
        Memory mainMemory = new MainMemory(10);
        Memory graphicsMemory = new MainMemory(10);

        // Create a memory space and add components to it.
        MemorySpace memorySpace = new MemorySpace();
        memorySpace.addMemoryRegion(mainMemory);
        memorySpace.addMemoryRegion(graphicsMemory);

        // Create a CPU and give it a memory space to work on.
        CPU cpu = new OR16(memorySpace);

        for (int n = 0; n < 10; n++) cpu.tick();

        ((OR16) cpu).print_registers();
        System.out.println(memorySpace.read(9));
        System.out.println(memorySpace.read(15));
    }
}
