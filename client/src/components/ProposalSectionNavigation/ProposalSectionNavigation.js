import React, {useState} from 'react';
import {Navigation} from 'baseui/side-navigation';

export default () => {
    const [location, setLocation] = useState('my-proposal');

    const navigationItems = [
        {title: 'My Proposals', itemId: 'my-proposal',},
        {title: 'All Proposals', itemId: 'all-proposal',},
    ];

    return (
        <Navigation
            items={navigationItems}
            activeItemId={location}
            onChange={({item}) => setLocation(item.itemId)}
        />
    );
};
