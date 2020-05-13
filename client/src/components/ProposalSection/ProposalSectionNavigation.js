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
  const [location, setLocation] = useState('activeItemId={location}');
  
  return (
  
      <Navigation
      items={ProposalSectionNavigation}
      activeItemId={location}
      onChange={({item}) => setLocation(item.itemId)}
    />
       
  );
};