package exp2;

public class SimulatedComputer {

    OperatingSystem os = new OperatingSystem();
    HardwareCard[] PCIEs = new HardwareCard[5];   // 五个 PCIE 插槽

    class NoSlotAvaliableException extends Exception {

        public NoSlotAvaliableException(String message) {
            super(message);
        }
    }

    class DeviceNotFoundException extends Exception {

        public DeviceNotFoundException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        HardwareCard soundCard = new HardwareCard(HardwareType.SOUND, "Sound Card");
        HardwareCard graphicsCard = new HardwareCard(HardwareType.GRAPHICS, "Graphics Card");
        HardwareCard networkCard = new HardwareCard(HardwareType.NETWORK, "Network Card");

        SimulatedComputer computer = new SimulatedComputer();
        computer.pluginHardware(soundCard);
        computer.pluginHardware(graphicsCard);
        computer.pluginHardware(networkCard);

        computer.os.poweron();
        soundCard.launch();
        graphicsCard.launch();
        networkCard.launch();

        computer.os.poweroff();
        soundCard.stop();
        graphicsCard.stop();
        networkCard.stop();


    }

    void pluginHardware(HardwareCard card) {
        try {
            for (int i = 0; i < PCIEs.length; i++) {
                if (PCIEs[i] == null) {
                    card.plugin();
                    PCIEs[i] = card;
                    System.out.println("[*] " + card.getName() + " has been plugged into PCIE slot " + (i + 1) + ".");
                    return;
                }
            }
            throw new NoSlotAvaliableException("[-] No available PCIE slot to plug in " + card.getName() + ".");
        } catch (NoSlotAvaliableException | HardwareCard.HardwareCardException e) {
            System.out.println(e.getMessage());
        }
    }

    void unplugHardware(HardwareCard card) {
        try {
            for (int i = 0; i < PCIEs.length; i++) {
                if (PCIEs[i] == card) {
                    card.unplug();
                    PCIEs[i] = null;
                    System.out.println("[*] " + card.getName() + " has been unplugged from PCIE slot " + (i + 1) + ".");
                    return;
                }
            }
            throw new DeviceNotFoundException("[-] " + card.getName() + " is not currently plugged in.");
        } catch (DeviceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}

enum HardwareType {
    SOUND, GRAPHICS, NETWORK
}

interface HardwareOperation {

    int getStatus();

    void plugin();   // 插入

    void unplug();   // 拔出

    int isPluggedIn();  // 是否插入

    void launch();   // 启动

    void stop(); // 停止

    HardwareType getType(); // 获取硬件类型

    String getName(); // 获取硬件名称
}

class HardwareCard implements HardwareOperation {

    private HardwareType type;
    private String name;
    private int pluggedIn = 0;   // 0 标识未插入，1 标识已插入
    private int isRunning = 0;   // 0 标识未运行，1 标识正在运行

    class HardwareCardException extends RuntimeException {

        public HardwareCardException(String message) {
            super(message);
        }
    }

    class AlreadyPluggedInException extends HardwareCardException {

        public AlreadyPluggedInException(String message) {
            super(message);
        }
    }

    class AlreadyUnpluggedException extends HardwareCardException {

        public AlreadyUnpluggedException(String message) {
            super(message);
        }
    }

    class AlreadyRunningException extends HardwareCardException {

        public AlreadyRunningException(String message) {
            super(message);
        }
    }

    class NotRunningException extends HardwareCardException {

        public NotRunningException(String message) {
            super(message);
        }
    }

    HardwareCard(HardwareType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public int getStatus() {
        return isRunning;
    }

    @Override
    public void plugin() {
        if (pluggedIn == 1) {
            throw new AlreadyPluggedInException("[-] " + name + " is already plugged in.");
        }
        pluggedIn = 1;
        System.out.println("[*] " + name + " has been plugged in.");
    }

    @Override
    public void unplug() {
        if (pluggedIn == 0) {
            throw new AlreadyUnpluggedException("[-] " + name + " is already unplugged.");
        }
        if (isRunning == 1) {
            throw new AlreadyRunningException("[-] " + name + " is currently running. Please stop it before unplugging.");
        }
        pluggedIn = 0;
        System.out.println("[*] " + name + " has been unplugged.");
    }

    @Override
    public int isPluggedIn() {
        return pluggedIn;
    }

    @Override
    public void launch() {
        if (pluggedIn == 0) {
            throw new AlreadyUnpluggedException("[-] " + name + " is not plugged in. Please plug it in before launching.");
        }
        if (isRunning == 1) {
            throw new AlreadyRunningException("[-] " + name + " is already running.");
        }
        isRunning = 1;
        System.out.println("[*] " + name + " has been launched.");
        System.out.println("[*] " + name + " is now running.");
    }

    @Override
    public void stop() {
        if (pluggedIn == 0) {
            throw new AlreadyUnpluggedException("[-] " + name + " is not plugged in. Please plug it in before stopping.");
        }
        if (isRunning == 0) {
            throw new NotRunningException("[-] " + name + " is not running.");
        }
        isRunning = 0;
        System.out.println("[*] " + name + " has been stopped.");
    }

    @Override
    public HardwareType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}

interface SystemOperations {

    void poweron();

    void poweroff();
}

class OperatingSystem implements SystemOperations {

    @Override
    public void poweron() {
        System.out.println("[*] System is powering on...");
    }

    @Override
    public void poweroff() {
        System.out.println("[*] System is powering off...");
    }
}
