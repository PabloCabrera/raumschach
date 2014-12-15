package raumschach.view;
import raumschach.event.GameEvent;

public interface RaumschachView {
	public void handleEvent (GameEvent event);
}
