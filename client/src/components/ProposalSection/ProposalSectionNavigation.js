import React, {useState} from 'react';
import {Navigation} from 'baseui/side-navigation';


const ProposalSectionNavigation = [

  {
    title: 'My Proposals',
    itemId: '/MyProposalsPage',
   
  },
  {
    title: 'All Proposals',
    itemId: '/AllProposalsPage',
  },
  
];

export default () => {
  const [location, setLocation] = useState('#level1.1.1');
  const [css] = useStyletron();
  return (
  
      <Navigation
      items={ProposalMenu}
      activeItemId={location}
      onChange={({item}) => setLocation(item.itemId)}
    />
       
  );
};