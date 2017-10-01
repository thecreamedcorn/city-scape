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

package View;

import Controller.MVCController;
import Model.MVCModel;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Scanner;

/**
 * Created by 18wgaboury on 5/7/2017.
 * This object is the main object that initializes the program but also is in charge of generating what the user actually
 * sees
 */
public class MVCView extends JFrame
	{
	private MVCModel model;
	private MVCController controller;

	private JLayeredPane lpane;
	private JButton randomize;
	private ZBufferedRaster image;
	private JComponent imagePanel;

	/**
	 * initializes the GUI and all creates or calls to code that creates all the initial application data
	 */
	public MVCView()
		{
		super("Cool 3D Renderer");

		model = new MVCModel(this);
		controller = new MVCController(model);

		this.setSize(model.getScreenWidth(), model.getScreenHeight());
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.image = new ZBufferedRaster(model.getScreenWidth(), model.getScreenHeight());

		lpane = new JLayeredPane()
			{
			public void paint(Graphics g)
				{
				super.paint(g);
				setBounds(0,0,model.getScreenWidth(),model.getScreenHeight());
				}
			};
		lpane.setBounds(0,0,model.getScreenWidth(),model.getScreenHeight());
		getContentPane().add(lpane);

		imagePanel = new JComponent()
			{
			public void paint(Graphics g)
				{
				setBounds(0,0,model.getScreenWidth(), model.getScreenHeight());
				g.drawImage(image.getRasterImage(), 0, 0, model.getScreenWidth(), model.getScreenHeight(), Color.BLACK, null);
				}
			};
		imagePanel.setBounds(0,0,model.getScreenWidth(), model.getScreenHeight());
		lpane.add(imagePanel, 0, 0);

		randomize = new JButton("Randomize");
		randomize.setSize(80, 30);
		randomize.setMargin(new Insets(0,0,0,0));
		lpane.add(randomize, 1, 0);

		controller.connectResizeComponent(this);
		controller.connectMouseListner(this);
		controller.connectRandomizeButtomListner(randomize);

		setVisible(true);
		controller.init();
		}

	/**
	 * called by the MVCModel whenever it changes
	 */
	public void update()
		{
		image.resetSize(model.getScreenWidth(), model.getScreenHeight());

		Collection<RenderTriangle> tris = (Collection<RenderTriangle>) model.getRenderTriangles().clone();

		for (RenderTriangle tri : tris)
			{ image.renderTriangle(tri); }

		for (RenderLine line : model.getRenderLines())
			{ image.renderLine(line); }

		repaint();
		}


	public static void main(String[] args) { new MVCView(); }
	}
