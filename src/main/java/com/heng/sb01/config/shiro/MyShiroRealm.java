package com.heng.sb01.config.shiro;

import com.heng.sb01.entity.User;
import com.heng.sb01.util.JwtUtil;
import io.ebean.Ebean;
import io.ebean.SqlRow;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAccount = new SimpleAuthorizationInfo();
        Set<Permission> setp = new HashSet<Permission>();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("loginUser");
        if (user != null) {
            String sql = "select distinct mf.funcurlid ,furl.funcurl " +
                    "from sy_userrolerela ur, sy_rolefuncrela rm,sy_menufuncrela mf,sy_funcurl furl " +
                    "where ur.roleid = rm.roleid and  rm.menuid = mf.menuid and mf.funcurlid = furl.id and ur.userid =:uid";
            List<SqlRow> list = Ebean.createSqlQuery(sql).setParameter("uid", user.id).findList();
            if (list != null && list.size() > 0) {
                for (SqlRow sqlRow : list) {
                    setp.add(new URIPermission(sqlRow.getString("funcurl")));
                }
            }
        }
        simpleAccount.addObjectPermissions(setp);
        return simpleAccount;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String username = JwtUtil.getUsername(token);
        if (StringUtils.isNotBlank(username)) {
            return new SimpleAuthenticationInfo(username, token, getName());
        } else {
            throw new UnknownAccountException();
        }
    }

    public MyShiroRealm() {
        this.setAuthenticationTokenClass(JWTToken.class);
    }
}
