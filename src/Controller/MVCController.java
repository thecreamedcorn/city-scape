/*
city-scape: a 3d scene of a city soft rendered in java
Copyright (C) 2017  Wil Gaboury

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/
*/

package Controller;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

/**
 * Created by 18wgaboury on 5/7/2017.
 * This is the controller class that responds to any inputs given by the user and then modifies the model object accordingly
 */
public class MVCController
	{
	private ActionListener randomizeButton;
	private ComponentListener windowResize;
	private MouseListener clickAction;
	private MouseMotionListener dragAction;
	private MouseWheelListener scrollEvent;

	private MVCModel model;

	/**
	 * the original X of the mouse on the screen when a drag event starts
	 */
	private int originalX;
	/**
	 * the original Y of the mouse on the screen when a drag event starts
	 */
	private int originalY;

	/**
	 * this is compatable with the MVCModel classes ModelMeshesModifier functional interface and is used in order to
	 * create the skyscraper scene by instaniating all the objects in the scene and adding them to the model object
	 * @param objs
	 */
	public static void createSkylineScene(Collection<Object3D> objs)
		{
		objs.clear();

		objs.add(new Object3D(
				new Rectangle3D(200,200, 0x53ff0e),
				new TransformationMatrix.XAxisRotation(Math.toRadians(90)).apply(
						new TransformationMatrix.Transpose(-100,0,-100)
				)));

		objs.add(new Object3D(new Rectangle3D(10, 200, 0x747171),
							  new TransformationMatrix.XAxisRotation(Math.toRadians(90)).apply(
									  new TransformationMatrix.Transpose(14,0.1,-100)
							  )));
		objs.add(new Object3D(new Rectangle3D(10, 200, 0x747171),
							  new TransformationMatrix.XAxisRotation(Math.toRadians(90)).apply(
									  new TransformationMatrix.Transpose(14,0.1,-100).apply(
									  		new TransformationMatrix.YAxisRotation(Math.toRadians(90))
									  )
							  )));

		objs.add(new Object3D(new Rectangle3D(10, 200, 0x747171),
							  new TransformationMatrix.XAxisRotation(Math.toRadians(90)).apply(
									  new TransformationMatrix.Transpose(14,0.1,-100).apply(
											  new TransformationMatrix.YAxisRotation(Math.toRadians(180))
									  )
							  )));
		objs.add(new Object3D(new Rectangle3D(10, 200, 0x747171),
							  new TransformationMatrix.XAxisRotation(Math.toRadians(90)).apply(
									  new TransformationMatrix.Transpose(14,0.1,-100).apply(
											  new TransformationMatrix.YAxisRotation(Math.toRadians(270))
									  )
							  )));

		double[][] blockLocations = {
				{0, 0},
				{38, 0},
				{38, 38},
				{0, 38},
				{-38, 38},
				{-38, 0},
				{-38, -38},
				{0, -38},
				{38, -38}
		};

		for(double[] loc : blockLocations)
			{
			objs.add(new Object3D(
					new Tower(10, 15, 50, 1, 1),
					new TransformationMatrix.Transpose(2 + loc[0], 0, 2 + loc[1])
			));
			objs.add(new Object3D(
					new Tower(10, 15, 50, 1, 1),
					new TransformationMatrix.Transpose(-12 + loc[0], 0, 2 + loc[1])
			));
			objs.add(new Object3D(
					new Tower(10, 15, 50, 1, 1),
					new TransformationMatrix.Transpose(-12 + loc[0], 0, -12 + loc[1])
			));
			objs.add(new Object3D(
					new Tower(10, 15, 50, 1, 1),
					new TransformationMatrix.Transpose(2 + loc[0], 0, -12 + loc[1])
			));
			}
		}

	/**
	 * instantiates all the listener objects with anonymous classes that correctly modify the model object when the user
	 * gives that action
	 * @param inModel the model object that should be updated when actions happen
	 */
	public MVCController(MVCModel inModel)
		{
		model = inModel;

		originalX = 0;
		originalY = 0;

		randomizeButton = new ActionListener()
			{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
				{ model.modifyModelMeshes(MVCController::createSkylineScene); }
			};

		windowResize = new ComponentListener()
			{
			@Override
			public void componentResized(ComponentEvent componentEvent)
				{
				model.resizeScreen(componentEvent.getComponent().getWidth(), componentEvent.getComponent().getHeight());
				}

			@Override
			public void componentMoved(ComponentEvent componentEvent)  {}
			@Override
			public void componentShown(ComponentEvent componentEvent)  {}
			@Override
			public void componentHidden(ComponentEvent componentEvent)  {}
			};
		
		clickAction = new MouseListener()
			{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
				{
				originalX = mouseEvent.getXOnScreen();
				originalY = mouseEvent.getYOnScreen();
				}

			@Override
			public void mouseClicked(MouseEvent mouseEvent) {}
			@Override
			public void mouseReleased(MouseEvent mouseEvent) {}
			@Override
			public void mouseEntered(MouseEvent mouseEvent) {}
			@Override
			public void mouseExited(MouseEvent mouseEvent) {}
			};
		
		dragAction = new MouseMotionListener()
			{
			@Override
			public void mouseDragged(MouseEvent mouseEvent)
				{
				int deltax = originalX - mouseEvent.getXOnScreen();
				int deltay = originalY - mouseEvent.getYOnScreen();

				originalX = mouseEvent.getXOnScreen();
				originalY = mouseEvent.getYOnScreen();

				TransformationMatrix trans = new TransformationMatrix.IdentityMatrix();

				if (!((Math.abs(model.getCamera().get(3, 0)) <= 5) &&
						(Math.abs(model.getCamera().get(3,2)) <= 5)) ||
						(deltay > 0 && model.getCamera().get(3,1) >= 0) ||
						(deltay < 0 && model.getCamera().get(3,1) <= 0))
					{
					trans.apply(new TransformationMatrix.WeirdXRotate(
									model.getCamera(),
									deltay * 0.005
					));
					}

				model.moveCamera(trans.apply(new TransformationMatrix.YAxisRotation(deltax * 0.005)));
				}

			@Override
			public void mouseMoved(MouseEvent mouseEvent) {}
			};

		scrollEvent = new MouseWheelListener()
			{
			@Override
			public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent)
				{
				double m = mouseWheelEvent.getPreciseWheelRotation();
				double x = model.getCamera().get(2,0);
				double y = model.getCamera().get(2,1);
				double z = model.getCamera().get(2,2);

				if (  !(Math.abs(model.getCamera().get(3,0)) < 5 &&
						Math.abs(model.getCamera().get(3,2)) < 5 &&
						m < 0) )
					{ model.moveCamera(new TransformationMatrix.Transpose( m * x, m * y, m * z)); }
				}
			};
		}

	/**
	 * class called at the start of the program that creates the initial scene by passing createSkylineScene to the
	 * MVCModel
	 */
	public void init()
		{ model.modifyModelMeshes(MVCController::createSkylineScene); }

	/**
	 * takes in a component and uses it as the input for where to listen for clicks, drags, and scrolling
	 * @param comp the component to listen to
	 */
	public void connectMouseListner(Component comp)
		{
		comp.addMouseListener(clickAction);
		comp.addMouseMotionListener(dragAction);
		comp.addMouseWheelListener(scrollEvent);
		}

	/**
	 * connects a button to a listener and uses it as input for resetting the scene
	 * @param comp the jbutton to listen to
	 */
	public void connectRandomizeButtomListner(JButton comp)
		{ comp.addActionListener(randomizeButton); }

	/**
	 * for connecting the window in order to update the model render area
	 * @param comp the frame component to connect
	 */
	public void connectResizeComponent(Frame comp)
		{ comp.addComponentListener(windowResize); }
	}
