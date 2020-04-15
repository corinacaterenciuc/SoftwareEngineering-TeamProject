import * as React from 'react';
import {
  HeaderNavigation,
  ALIGN,
  StyledNavigationItem as NavigationItem,
  StyledNavigationList as NavigationList,
} from 'baseui/header-navigation';
import {StyledLink as Link} from 'baseui/link';
import {Button, SHAPE} from 'baseui/button';

export default () => (
  <HeaderNavigation>
    <NavigationList $align={ALIGN.left}>
      <NavigationItem>
      <h1>CMS</h1>
      </NavigationItem>
    </NavigationList>
    <NavigationList $align={ALIGN.center} />
    <NavigationList $align={ALIGN.right}>
      <NavigationItem>
        <Link href="#basic-link1">Home</Link>
      </NavigationItem>
      <NavigationItem>
        <Link href="#basic-link2">Conferences</Link>
      </NavigationItem>
      <NavigationItem>
        <Link href="#basic-link3">Contact</Link>
      </NavigationItem>
      <NavigationItem>
        <Link href="#basic-link4">Sign up</Link>
      </NavigationItem>
    </NavigationList>
    <NavigationList $align={ALIGN.right}>
      <NavigationItem>
        <Button  onClick={() => alert("")}>Log in</Button>
      </NavigationItem>
    </NavigationList>
  </HeaderNavigation>
);