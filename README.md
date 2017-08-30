# TIC

*TIC* allows to efficiently develop GUI components which can be used both in *Swing* and *SWT* projects. The graphical output, as well as mouse and and keyboard reactions of a component need to be implemented only once and *TIC* allows to use the component in both toolkits. Therefore it provides adapter classes converting both paint methods and mouse and key events between the two toolkits.

Developers who need to offer components that work with *Swing* and *SWT* in the same way (e.g. developers of GUI component libraries) can make use of *TIC* to ease up the implementation process. An example for a GUI library that offers all its components in a *Swing* and *SWT* version is [*LibrAlign*](https://github.com/bioinfweb/LibrAlign) for which *TIC* was initially created.

*TIC* is distributed under [LGPL](http://bioinfweb.info/TIC/License). More information can be found at http://bioinfweb.info/TIC/.

## Getting started

The [documentation](http://bioinfweb.info/TIC/Documentation) and the [JavaDoc](http://bioinfweb.info/TIC/Documentation/API/Latest/) are available on the website. A basic demo application can be found [here](https://github.com/bioinfweb/TIC/tree/master/demo/info.bioinfweb.tic.demo.sierpinski/src/info/bioinfweb/tic/demo/sierpinski).

If you have further questions, feel free to contact [@BenStoever](https://github.com/BenStoever) at stoever@bioinfweb.info.

## Source code

This *GitHub* repository in a synchronized mirror of the [master repository at bioinfweb](http://bioinfweb.info/Code/sventon/repos/TIC/list/). Feedback and pull requests are welcome. Synchronization was made possible by [*SubGit*](https://subgit.com/).

## License

*TIC* are distrubuted under [GNU General Lesser Public License Version 3](http://bioinfweb.info/TIC/License).

## Binary releases

Binary releases can be found at http://bioinfweb.info/TIC/Download. Maven is supported.
