package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.menu.MenuId;

public class MenuNotFoundException extends RuntimeException {

    public MenuNotFoundException(MenuId id) {
        super("Menu not found with id: " + id.value());
    }

    public MenuNotFoundException(String message) {
        super(message);
    }

}
