import React from 'react';
import {BrowserRouter, Redirect, Route, Switch} from 'react-router-dom';
import {getCategoryForRole} from '../util/routeManager';
import {useSelector} from 'react-redux';
import {StoreState} from '../state';

const ApplicationRouter = () => {
    const role = useSelector((store: StoreState) => store.auth.role);
    const myCategory = getCategoryForRole(role);
    const Layout = myCategory.layout;

    return (
        <BrowserRouter>
            <Layout>
                <Switch>
                    {myCategory.routes.map(route => (
                        <Route exact path={route.path} component={route.Screen} key={route.path}/>
                    ))}
                    <Route render={() => <Redirect to="/"/>}/>
                </Switch>
            </Layout>
        </BrowserRouter>
    );

};

export default ApplicationRouter;