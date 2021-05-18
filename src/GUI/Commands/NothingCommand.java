package GUI.Commands;

public class NothingCommand  implements Command{

    @Override
    public void execute() {
        System.out.println("Pai...nimic!");
    }
}