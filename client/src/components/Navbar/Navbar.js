import * as React from 'react';
import {
  HeaderNavigation,
  ALIGN,
  StyledNavigationItem as NavigationItem,
  StyledNavigationList as NavigationList,
} from 'baseui/header-navigation';
import {StyledLink as Link} from 'baseui/link';

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
        <Link href="/conferences">Conferences</Link>
      </NavigationItem>
      <NavigationItem>
          <Link href="#/proposals">Proposals</Link>
      </NavigationItem>
      <NavigationItem>
          <Link href="/sign-out" style={{marginRight: "1em"}}>Sign Out</Link>
      </NavigationItem>
    </NavigationList>
  </HeaderNavigation>
);