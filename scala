
https://twitter.github.io/scala_school/basics.html


Why Scala?
Expressive
	First-class functions
	Closures
Concise
	Type inference
	Literal syntax for function creation
Java interoperability
	Can reuse java libraries
	Can reuse java tools
	No performance penalty

Think Scala
	Scala is not just a nicer Java. You should learn it with a fresh mind- you will get more out of these classes.



Anonymous Functions
	You can create anonymous functions.
	scala> (x: Int) => x + 1
	res2: (Int) => Int = <function1>

Function
	def timesTwo(i: Int): Int = {
  		println("hello world")
		i * 2
	}

underscore (_)  - unnamed magical wildcard - mean different things in different context
	scala> def adder(m: Int, n: Int) = m + n
	adder: (m: Int,n: Int)Int
	
	scala> val add2 = adder(2, _:Int)
	add2: (Int) => Int = <function1>

	scala> add2(3)
	res50: Int = 5

Curried Function
	apply some arguments to your function now and others later
	
	scala> def multiply(m: Int)(n: Int): Int = m * n
	multiply: (m: Int)(n: Int)Int
	
	scala> multiply(2)(3)
	res0: Int = 6

	scala> val timesTwo = multiply(2) _
	timesTwo: (Int) => Int = <function1>

	scala> timesTwo(3)
	res1: Int = 6


Variable length arguments
	def capitalizeAll(args: String*) = {
	  args.map { arg =>
    	arg.capitalize
	  }
	}

Constructor
	Constructors aren’t special methods, they are the code outside of method definitions in your class
	
	class Calculator(brand: String) {
  	/**
	 * A constructor.
   	 */
  	val color: String = if (brand == "TI") {
    	"blue"
	  } else if (brand == "HP") {
    	"black"
	  } else {
    	"white"
	  }

	  // An instance method.
	  def add(m: Int, n: Int): Int = m + n
	}


	scala> val calc = new Calculator("HP")
	calc: Calculator = Calculator@1e64cc4d

	scala> calc.color
	res0: String = black




scala> capitalizeAll("rarity", "applejack")
res2: Seq[String] = ArrayBuffer(Rarity, Applejack)



Collection
Tuple
	A tuple groups together simple logical collections of items without using a class
		val hostPort = ("localhost",80)
		hostPort: (String, Int) = (localhost, 80)
	Unlike case classes, they dont have named accessors, instead they have accessors
	that are named by their position and is 1-based rather than 0-based
		hostPort._1 => localhost
		hostPort._2 => 80

	simply making tuple of 2 values using ->
		1 -> 2
		response: (Int, Int) = (1,2)

Maps
	Map(1 -> 2)
	Map("foo" -> "bar")
	
	create Tuple as entry to map
	
