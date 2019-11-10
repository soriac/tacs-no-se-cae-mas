import Login from '../../screens/Login';
import AdminDashboard from '../../screens/Admin/AdminDashboard';
import Layout from '../../components/Layout';
import UserDashboard from '../../screens/User/UserDashboard';
import RepoSearch from '../../screens/Admin/RepoSearch';
import UserRepoSearch from '../../screens/User/RepoSearch';
import UserScreen from '../../screens/Admin/User';
import MyRepos from '../../screens/User/MyRepos';
import Users from '../../screens/Admin/Users';
import Compare from '../../screens/Admin/Compare';
import CreateRepo from '../../screens/Admin/CreateRepo';
import {Role} from '../../api/types';
import Signup from '../../screens/Signup';
import Contributors from "../../screens/Contributors";

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
            {
                name: 'Signup',
                path: '/signup',
                isScreen: false,
                icon: 'none',
                Screen: Signup
            },
        ]
    },
    {
        requiredRole: Role.user,
        layout: Layout,
        routes: [
            {
                name: 'Home',
                path: '/',
                isScreen: true,
                icon: 'home',
                Screen: UserDashboard
            },
            {
                name: 'Repository search',
                path: '/repos',
                isScreen: true,
                icon: 'search',
                Screen: UserRepoSearch
            },
            {
                name: 'My repos',
                path: '/myRepos',
                isScreen: true,
                icon: 'list',
                Screen: MyRepos
            },
            {
                name: 'Contributors',
                path: '/repos/:id/contributors',
                isScreen: false,
                icon: 'person',
                Screen: Contributors
            }

        ]
    },
    {
        requiredRole: Role.admin,
        layout: Layout,
        routes: [
            {
                name: 'Home',
                path: '/',
                isScreen: true,
                icon: 'home',
                Screen: AdminDashboard
            },
            {
                name: 'Repository Search',
                path: '/repos',
                isScreen: true,
                icon: 'search',
                Screen: RepoSearch
            },
            {
                name: 'Users',
                path: '/users',
                isScreen: true,
                icon: 'person',
                Screen: Users
            },
            {
                name: 'Compare',
                path: '/compare',
                isScreen: true,
                icon: 'comparison',
                Screen: Compare
            },
            {
                name: 'User',
                path: '/users/:id',
                isScreen: false,
                icon: 'person',
                Screen: UserScreen
            },
            {
                name: 'Contributors',
                path: '/repos/:id/contributors',
                isScreen: false,
                icon: 'person',
                Screen: Contributors
            },
            {
                name: 'CreateRepo',
                path: '/new',
                isScreen: true,
                icon: 'add',
                Screen: CreateRepo
            }
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