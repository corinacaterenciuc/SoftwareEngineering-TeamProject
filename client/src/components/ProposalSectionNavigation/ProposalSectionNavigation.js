import React, {useState} from 'react';
import {Navigation} from 'baseui/side-navigation';

export default () => {
    const [location, setLocation] = useState('my-proposals');

    const navigationItems = [
        { title: 'My Proposals', itemId: 'my-proposals', },
        { title: 'All Proposals', itemId: 'all-proposals', },
    ];

    return (
        <Navigation
            items={navigationItems}
            activeItemId={location}
            onChange={({item}) => setLocation(item.itemId)}
        />
    );
};
