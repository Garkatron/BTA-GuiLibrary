package deus.guilib.element.interfaces.element;

import deus.guilib.element.config.derivated.ElementConfig;
import deus.guilib.resource.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.List;

public interface IElement {
	// Métodos relacionados con la textura del elemento
	Texture getTexture();
	IElement setTexture(Texture texture);

	// Métodos para gestionar la posición del elemento
	int getX();
	IElement setX(int x);



	int getY();
	IElement setY(int y);

	IElement setPosition(int x, int i);

	int getWidth();
	int getHeight();

	// Métodos para configurar el elemento
	ElementConfig getConfig();
	void setConfig(ElementConfig config);
	IElement config(ElementConfig AElementConfig);

	// Métodos para manejar hijos del elemento
	List<IElement> getChildren();
	IElement setChildren(IElement... children);
	IElement addChildren(IElement... children);

	// Métodos para configurar dependencias
	void setMc(Minecraft mc);
	boolean hasDependency();

	// Método para dibujar el elemento
	void draw();

	IElement setPositioned(boolean positioned);

	boolean isPositioned();

	String getGroup();
	String getSid();
	IElement setSid(String sid);
	IElement setGroup(String group);

}
