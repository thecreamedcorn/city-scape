package Model;

import Model.Mesh.Mesh;
import Model.Mesh.MeshImplementationHelper;

/**
 * Created by 18wgaboury on 5/7/2017.
 * stores both a mesh and an initial model to world transformation matrix
 */
public class Object3D
	{
	private Mesh model;
	private TransformationMatrix modelToWorld;

	/**
	 * makes a new object 3D using a Mesh and a transformation matrix
	 * @param model a model
	 * @param modelToWorld a transformation matrix
	 */
	public Object3D(Mesh model, TransformationMatrix modelToWorld)
		{
		this.model = model;
		this.modelToWorld = modelToWorld;
		}

	/**
	 * creates an empty object3D
	 */
	public Object3D()
		{
		model = new MeshImplementationHelper(){};
		modelToWorld = new TransformationMatrix.IdentityMatrix();
		}

	/**
	 * gets the mesh
	 * @return the mesh
	 */
	public Mesh getModel()
		{ return model; }

	/**
	 * sets the mesh object
	 * @param model a mesh object
	 */
	public void setModel(Mesh model)
		{ this.model = model; }

	/**
	 * gets the current model to world transformation matrix
	 * @return the current model to world transformation matrix
	 */
	public TransformationMatrix getModelToWorld()
		{ return modelToWorld; }

	/**
	 * sets the current model to world transformation matrix
	 * @param modelToWorld the current model to world transformation matrix
	 */
	public void setModelToWorld(TransformationMatrix modelToWorld)
		{ this.modelToWorld = modelToWorld; }
	}
