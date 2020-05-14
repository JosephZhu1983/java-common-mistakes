package org.geekbang.time.commonmistakes.dataandcode.codeinject;

import jdk.nashorn.tools.Shell;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.FilePermission;
import java.lang.reflect.ReflectPermission;
import java.security.*;
import java.util.Arrays;
import java.util.List;
import java.util.PropertyPermission;

@Slf4j
public class ScriptingSandbox {
    private static ThreadLocal<Boolean> needCheck = ThreadLocal.withInitial(() -> false);
    private ScriptEngine scriptEngine;
    private AccessControlContext accessControlContext;
    private SecurityManager securityManager;

    public ScriptingSandbox(ScriptEngine scriptEngine) throws InstantiationException {
        this.scriptEngine = scriptEngine;
        securityManager = new SecurityManager() {
            //仅在需要的时候检查权限
            @Override
            public void checkPermission(Permission perm) {
                if (needCheck.get() && accessControlContext != null) {
                    super.checkPermission(perm, accessControlContext);
                }
            }
        };
        //设置执行脚本需要的权限
        setPermissions(Arrays.asList(
                new RuntimePermission("getProtectionDomain"),
                new PropertyPermission("jdk.internal.lambda.dumpProxyClasses", "read"),
                new FilePermission(Shell.class.getProtectionDomain().getPermissions().elements().nextElement().getName(), "read"),
                new RuntimePermission("createClassLoader"),
                new RuntimePermission("accessClassInPackage.jdk.internal.org.objectweb.*"),
                new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.*"),
                new RuntimePermission("accessDeclaredMembers"),
                new ReflectPermission("suppressAccessChecks")
        ));
    }

    public void setPermissions(List<Permission> permissionCollection) {
        Permissions perms = new Permissions();

        if (permissionCollection != null) {
            for (Permission p : permissionCollection) {
                perms.add(p);
            }
        }

        ProtectionDomain domain = new ProtectionDomain(new CodeSource(null, (CodeSigner[]) null), perms);
        accessControlContext = new AccessControlContext(new ProtectionDomain[]{domain});
    }

    public Object eval(final String code) {

        SecurityManager oldSecurityManager = System.getSecurityManager();
        System.setSecurityManager(securityManager);
        needCheck.set(true);
        try {
            //在AccessController的保护下执行脚本
            return AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                try {
                    return scriptEngine.eval(code);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                return null;
            }, accessControlContext);

        } catch (Exception ex) {
            log.error("抱歉，无法执行脚本 {}", code, ex);
        } finally {
            needCheck.set(false);
            System.setSecurityManager(oldSecurityManager);
        }
        return null;
    }
}