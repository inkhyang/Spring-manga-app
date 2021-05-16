package com.inkhyang.pet.security;

/*@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final MyUserRepository myUserRepository;

    public CustomAuthenticationProvider(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();

        MyUser myUser = myUserRepository.findByUsername(userName);
        if (myUser == null) {
            throw new BadCredentialsException("Unknown user " + userName);
        }

        UserDetails principal = User.builder()
                .username(myUser.getUsername())
                .roles(myUser.getRoles().iterator().next().toString())
                .build();
        return new UsernamePasswordAuthenticationToken(principal, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}*/
