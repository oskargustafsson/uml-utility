package algorithm.force;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.geom.Point2D;

/*********************************************************
 *  Vector2D.java                                        *
 *  ===================================================  *
 *                                                       *
 *  Made to facilitate a more esthetic and efficient     *
 *  implementation of assignment 3, OO Java, summer      *
 *  2002.                                                *
 *                                                       *
 *  Mike Anderson                                        *
 *                                                       *
 *                                                       *
 *********************************************************/


public class Vector3D
{
	public double x = 0.0;
	public double y = 0.0;
	public double z = 0.0;

	public Vector3D() {}
	public Vector3D( double x, double y, double z ) { this.x = x; this.y = y; this.z = z; }
	public Vector3D( Vector3D vec ) { this.x = vec.x; this.y = vec.y; this.z = vec.z; }
	public Vector3D( Component comp ) { this.x = comp.getX(); this.y = comp.getY(); this.z = 0.0; }


	//  BASIC ASSIGNMENT METHODS
	//-------------------------------
	//
	public void setTo( Vector3D vec )
	{
		x = vec.x;
		y = vec.y;
		z = vec.z;

		Container c;
	}

	public void setTo( double x, double y, double z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String toString()
	{
		return new String( "[" + (int)x + "," + (int)y + "," + (int)z + "]" );
	}



	//  MATHEMATICAL OPERATIONS
	//-------------------------------
	//  The core functionality of
	//  geometrical vectors
	//

	//  1. Vector length and normalization
	//-----------------------------------------
	//
	public double length()
	{
		return Math.sqrt( (x*x) + (y*y) + (z*z) );
	}


	public Vector3D normalize()
	{
		double len = length();

		if( len != 0.0 )
		{
			x /= len;
			y /= len;
			z /= len;
		}
		else
		{
			x = 0.0;
			y = 0.0;
			z = 0.0;
		}

		return new Vector3D( this );
	}


	//  2. Addition methods
	//-------------------
	//
	public Vector3D add( Vector3D vec )
	{
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;

		return new Vector3D( this );
	}

	public Vector3D add( double x, double y, double z )
	{
		this.x += x;
		this.y += y;
		this.z += z;

		return new Vector3D( this );
	}


	//  3. Subtraction methods
	//-----------------------
	//
	public Vector3D sub( Vector3D vec )
	{
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;

		return new Vector3D( this );
	}

	public Vector3D sub( double x, double y, double z )
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;

		return new Vector3D( this );
	}


	//  4. Multiplication methods
	//-------------------------
	//
	public Vector3D dot( Vector3D vec )
	{
		this.x *= vec.x;
		this.y *= vec.y;
		this.z *= vec.z;

		return new Vector3D( this );
	}

	public Vector3D dot( double x, double y, double z )
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;

		return new Vector3D( this );
	}

	public Vector3D mul( double scalar )
	{
		x *= scalar;
		y *= scalar;
		z *= scalar;

		return new Vector3D( this );
	}

	public Vector3D unit() {
		return new Vector3D( this ).normalize();
	}
}