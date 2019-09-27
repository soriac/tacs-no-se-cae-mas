import React, {useEffect, useState} from 'react';
import styled from 'styled-components';

import {Button, Drawer, Navbar} from '@blueprintjs/core';
import {Alignment} from '@blueprintjs/core/lib/esm/common/alignment';
import {getCategoryForRole} from '../../util/routeManager';
import {useDispatch, useSelector} from 'react-redux';
import {setDrawer} from '../../state/ui/actions';
import {StoreState} from '../../state';
import DrawerItem from './DrawerItem';
import {logOut} from '../../state/auth/actions';

const Root = styled.div`
    width: 100vw;
    overflow-x: hidden;
    
    padding-bottom: 1.5rem;
    
    & > :first-child {
      margin-bottom: 1.5rem;
    }
`;

function getBestWidth() {
    return Math.min(window.innerWidth - 60, 360);
}

type Props = {}
const Layout: React.FC<Props> = ({children}) => {
    const open = useSelector((store: StoreState) => store.ui.drawerOpen);
    const role = useSelector((store: StoreState) => store.auth.role);
    const dispatch = useDispatch();

    const [drawerWidth, setWidth] = useState(getBestWidth());
    useEffect(() => window.addEventListener('resize', () => setWidth(getBestWidth())), []);

    const category = getCategoryForRole(role);
    const items = category.routes
        .filter(route => route.isScreen)
        .map(route => ({
            href: route.path,
            icon: route.icon,
            text: route.name,
            handleClose: () => dispatch(setDrawer(false))
        }));

    return (
        <Root>
            <Drawer position='left' size={drawerWidth} onClose={() => dispatch(setDrawer(false))} isOpen={open}>
                {items.map(item =>
                    <DrawerItem {...item} key={item.href}/>
                )}
            </Drawer>
            <Navbar>
                <Navbar.Group align={Alignment.LEFT}>
                    <Button icon="menu" onClick={() => dispatch(setDrawer(true))}/>
                    <Navbar.Divider/>
                    <Navbar.Heading>No se cae más!</Navbar.Heading>
                </Navbar.Group>

                <Navbar.Group align={Alignment.RIGHT}>
                    <Button icon="log-out" onClick={() => dispatch(logOut())}/>
                </Navbar.Group>
            </Navbar>
            {children}
        </Root>
    );
};

export default Layout;