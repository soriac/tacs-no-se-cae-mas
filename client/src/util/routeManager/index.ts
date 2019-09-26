import {Role} from '../../state/auth/types';
import Login from '../../screens/Login';
import AdminDashboard from '../../screens/Admin';
import Layout from '../../components/Layout';
import UserDashboard from '../../screens/User';

// isScreen is true when a route should show up in navigation bars
type Route = {
    name: string;
    path: string;

    isScreen: boolean;
    icon: string;
    Screen: any;
};

type Category = {
    requiredRole: Role;
    layout: any;
    routes: Route[];
}

const routes: Category[] = [
    {
        requiredRole: Role.anon,
        layout: ({children}: any) => children,
        routes: [
            {
                name: 'Login',
                path: '/',
                isScreen: false,
                icon: 'none',
                Screen: Login
            },
        ]
    },
    {
        requiredRole: Role.user,
        layout: Layout,
        routes: [
            {
                name: 'Dashboard',
                path: '/',
                isScreen: true,
                icon: 'none',
                Screen: UserDashboard
            }
        ]
    },
    {
        requiredRole: Role.admin,
        layout: Layout,
        routes: [
            {
                name: 'Dashboard',
                path: '/',
                isScreen: true,
                icon: 'none',
                Screen: AdminDashboard
            },
        ]
    }
];

export function getCategoryForRole(role: Role): Category {
    const route = routes.find(route => route.requiredRole === role);

    if (!route) {
        return routes[0];
    }

    return route;
}

export default routes;