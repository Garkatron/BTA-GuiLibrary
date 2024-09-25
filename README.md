# Gui-Library/Framework

#### Better than adventure
This mod uses a modified version of Fabric (Babric) and is designed only for Better than Adventure, a heavily modified version of Minecraft b1.7.3! For more information, join the discord server provided on this projects

# Description

The **Deus GUI Library** is a flexible and extensible framework designed for creating graphical user interfaces (GUIs) within the BTA Minecraft environment. This library aims to provide developers with a robust set of tools to manage various GUI components while maintaining the integrity of the original Minecraft code.

# Key Features

- **Declarative Programming Style**: Define interfaces declaratively for easier design.
- **Intuitive Interface**: User-friendly API for managing GUI elements.
- **Few-Argument Constructors**: Simplify element creation with fewer parameters.
- **Flexible Configuration**: Tailor element settings to your needs.
- **Auto-Generated Slots**: Seamlessly integrate with `Container.class`.
- **Theme System**: Apply consistent themes to your interfaces.
- **Node Architecture**: Manage elements hierarchically.
- **Foreground Text**: Render clear text for information display.

# Current elements
* Button
* ClickableElement
* Column
* DraggableElement
* FreeElement
* Panel
* PlayerInventory
* ProgressBar
* Row
* ScrollBar
* Slot
* Text
* TextArea
* TextField

# **Philosophy**

The **philosophy** of this framework is to avoid altering the existing code of **Minecraft**. The design aims to keep the original code intact while providing an **extensible** and **flexible** system for managing **Graphical User Interfaces (GUIs)**.

# **How It Works**

This framework employs a **hierarchy of nodes**, where a parent node contains child nodes. It utilizes the following classes:

- **`PageGui`**: A **Vanilla** GUI that has been modified to incorporate a **SPA Router** for managing different pages. This class is preferred for creating GUIs.
- **`AdvancedContainer`**: An extended version of the `Container` class, adapted to place slots according to the positions of the elements.


![GuiLibLogo](https://github.com/user-attachments/assets/4d5fb899-3012-433f-9b85-d0a0d25104fa)
