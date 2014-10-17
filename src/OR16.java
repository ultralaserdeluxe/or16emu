/**
 * Created by kebabdjuret on 2014-10-07.
 */
public class OR16 implements CPU {
    // Memory
    private final int MEMSIZE = 256;
    public int[] memory;

    // Registers
    private int pc = 0;
    private int ip = 0;
    private int acc = 0;
    private int sp = 0;
    private int xr = 0;

    // Emulation state
    private boolean executing = true;

    public OR16() {
        memory = new int[MEMSIZE];
        memory[0] = 12; // LDA #16
        memory[1] = 16;
        memory[2] = 20; // STA #5
        memory[3] = 5;
        memory[4] = 248; // HALT
    }

    @Override
    public void tick() {
        if (!executing) return;

        // Fetch
        ip = memory[pc++];

        // Execute
        switch (ip >> 3) {
            // NOP
            case 0:
                nop();
                break;
            // LDA
            case 1:
                lda();
                break;
            // STA
            case 2:
                sta();
                break;
            // LDX
            case 3:
                ldx();
                break;
            // STX
            case 4:
                stx();
                break;
            // LDS
            case 5:
                lds();
                break;
            // STS
            case 6:
                sts();
                break;
            // PUSH
            case 7:
                push();
                break;
            // PULL
            case 8:
                pull();
                break;
            // HALT
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
        pc = ip = acc = sp = xr = 0;
        executing = true;
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
        memory[fetch_operand(ip)] = acc;
    }

    private void ldx() {
        System.out.println("LDX");
        xr = fetch_operand(ip);
    }

    private void stx() {
        System.out.println("STX");
        memory[fetch_operand(ip)] = xr;
    }

    private void lds() {
        System.out.println("LDS");
        sp = fetch_operand(ip);
    }

    private void sts() {
        System.out.println("STS");
        memory[fetch_operand(ip)] = sp;
    }

    private void push() {
        System.out.println("PUSH (not implemented)");
    }

    private void pull() {
        System.out.println("PULL (not implemented)");
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
                address = memory[pc++];
                operand = memory[address];
                break;
            // Indirect addressing
            case 0x1:
                address = memory[pc++];
                operand = memory[memory[address]];
                break;
            // Indexed addressing
            case 0x2:
                address = memory[pc++] + xr;
                operand = memory[address];
                break;
            // Relative addressing
            case 0x3:
                address = pc + 2 + memory[pc++];
                operand = memory[address];
                break;
            // Immediate addressing
            case 0x4:
                operand = memory[pc++];
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
        OR16 cpu = new OR16();

        for (int n = 0; n < 10; n++) cpu.tick();

        System.out.print(cpu.memory[5]);
    }
}
