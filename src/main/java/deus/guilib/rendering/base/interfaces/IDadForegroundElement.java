package deus.guilib.rendering.base.interfaces;

import deus.guilib.rendering.base.foreground.ForegroundElement;

import java.util.List;

public interface IDadForegroundElement {
	List<ForegroundElement> getChildren();
	ForegroundElement setChildren(ForegroundElement...children);
	ForegroundElement addChildren(ForegroundElement...children);

}
