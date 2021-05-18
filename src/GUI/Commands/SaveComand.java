package GUI.Commands;

import SQLite.SQLite;

public class SaveComand implements Command{
    @Override
    public void execute() {
        SQLite.getInstance().save();
    }
}
