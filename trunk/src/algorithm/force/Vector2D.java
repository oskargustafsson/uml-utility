package algorithm.force;

import java.awt.Component;

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


public class Vector2D
{
    public double x = 0.0;
    public double y = 0.0;

    public Vector2D() {}
    public Vector2D( double x, double y ) { this.x = x; this.y = y; }
    public Vector2D( Vector2D vec ) { this.x = vec.x; this.y = vec.y; }
    public Vector2D( Component comp ) { this.x = comp.getX(); this.y = comp.getY(); }


//  BASIC ASSIGNMENT METHODS
//-------------------------------
//
    public void setTo( Vector2D vec )
    {
        x = vec.x;
        y = vec.y;
    }

    public void setTo( double x, double y )
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return new String( "[" + (int)x + "," + (int)y + "]" );
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
        return Math.sqrt( (x*x) + (y*y) );
    }


    public Vector2D normalize()
    {
        double len = length();

        if( len != 0.0 )
        {
            x /= len;
            y /= len;
        }
        else
        {
            x = 0.0;
            y = 0.0;
        }

        return new Vector2D( this );
    }


//  2. Addition methods
//-------------------
//
    public Vector2D add( Vector2D vec )
    {
        this.x += vec.x;
        this.y += vec.y;

        return new Vector2D( this );
    }

    public Vector2D add( double x, double y )
    {
        this.x += x;
        this.y += y;

        return new Vector2D( this );
    }


//  3. Subtraction methods
//-----------------------
//
    public Vector2D sub( Vector2D vec )
    {
        this.x -= vec.x;
        this.y -= vec.y;

        return new Vector2D( this );
    }

    public Vector2D sub( double x, double y )
    {
        this.x -= x;
        this.y -= y;

        return new Vector2D( this );
    }


//  4. Multiplication methods
//-------------------------
//
    public Vector2D mul( Vector2D vec )
    {
        this.x *= vec.x;
        this.y *= vec.y;

        return new Vector2D( this );
    }

    public Vector2D mul( double x, double y )
    {
        this.x += x;
        this.y += y;

        return new Vector2D( this );
    }

    public Vector2D mul( double scalar )
    {
        x *= scalar;
        y *= scalar;

        return new Vector2D( this );
    }
    
    public Vector2D unit() {
	return new Vector2D( this ).normalize();
    }
}