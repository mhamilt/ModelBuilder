# Model Builder

Original Model Builder Library with some additions to allow for easy version control.

## Building

The library is currently built in the command line using the processing `javac` binary directly.

The process is outlined i the [`build.sh`](./build.sh) script, but at the moment it is less of a script and more a guide for instructions.

There are some missing steps as well which assume the library is a particular current state. It is recommended to first go to the [Releases](https://github.com/mhamilt/ModelBuilder/releases/), the latest being [`0.9.0`](https://github.com/mhamilt/ModelBuilder/releases/tag/0.9.0) and taking the [`modelbuilderMk2-0136-20140719.zip`](https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/modelbuilderMk2-0136-20140719.zip) from there and unzipping into your `~/Documents/Processing/libraries/` directory.

After that run through the build script and it should hopefully make sense. If it doesn't make sense then that is a good sign.

## Why?

The purpose behind this was as an example in the problems of software sustainability. A student approached me about creating a [3D printed record](https://www.instructables.com/id/3D-Printed-Record/). The "instructable" for that project is not sufficient as it was created at a time when the tools it used actually functioned.

So far a interpretation of that original sketch [has yielded an `.stl` that looks right](https://gist.github.com/mhamilt/04c68da3aab818bf326b725af0a2974e), but no test has been conducted with printing.

## Dependency Links

- https://archive.apache.org/dist/commons/math/binaries/commons-math3-3.6.1-bin.zip
- https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/apacheCommonsMathRotations.jar.zip
- https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/poly2tri.jar.zip
- https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/ant-contrib-1.0b3.zip
- https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/geomerative.zip
  
## Original Description

ModelbuilderMk2 (previously Modelbuilder) is a computational geometry library for Processing. Its focus is to facilitate parametric and generative modeling of geometry, while minimizng complex math and repetitive code where possible. The library is based on a set of simplified data structures with corresponding workflows:

UVertexList represents paths and edges, comprised of UVertex objects. UGeo stores mesh data and automates common mesh-generating operations (quad strips, triangle fans), providing tools to manipulate meshes as unified entities. Additional tools include mesh selection and traversal, export to STL and various time-saving hacks.

**ModelbuilderMk2 prioritizes flexibility and ease of development** over optimal performance, but is still efficient enough to support realtime uses. Its primary design objective is to lower the threshold for non-experts wanting to experiment with computational geometry, without requiring an in-depth understanding of the data structures or math involved.

Borrowing an old concept recently popularized by JavaScript, ModelbuilderMk2 uses operator chaining wherever possible, which makes code more compact and simplifies geometry manipulation. Admittedly, operator chaining can make code harder to read, but if combined with code auto-completion (PDE-X, Eclipse) it greatly simplifies development.

------------------------------

### ModelbuilderMk2 and ITP NYU
**ModelbuilderMk2 was created during my Fall 2013 research residency at NYU ITP,** coinciding with my teaching a class on [Parametric Design and Digital Fabrication](http://workshop.evolutionzone.com/itp-2013-parametric-design-for-digital-fabrication/). ModelbuilderMk2 ended up being the major focus of my residency, giving me the luxury of time to rewrite the library from scratch while adding features I discovered a need for in class.

ModelbuilderMk2 also became a primary tool for teaching principles of parametric form in terms of low-level computational logic, providing an understanding of how geometry is constructed vertex-by-vertex as well as how simple generative principles can be injected into every aspect of the form-generating process to manipulate the outcome.

[Code & Form blog](http://workshop.evolutionzone.com/) / [Marius Watz](http://mariuswatz.com)

------------------------------
