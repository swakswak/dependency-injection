package toy.swak.dependency.injector;

import toy.swak.scanner.Package;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public interface DependencyInjector {
    void injectDependency(Package pkg);
}
