package yacekbass.ear.config

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer

class SessionInitializer : AbstractHttpSessionApplicationInitializer(JdbcConfig::class.java)
