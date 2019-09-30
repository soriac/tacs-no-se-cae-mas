import React from 'react';
import {Spinner} from '@blueprintjs/core';
import UserComponent from '../../../components/User';
import {User} from '../../../api/types';
import {ContentContainer} from '../../../components/ContentContainer';
import {Root} from '../../../components/Root';

type Props = {
    user: User | undefined
    loading: boolean
}
const Layout: React.FC<Props> = ({user, loading}) => {

    return (
        <Root>
            <ContentContainer>
                <h2>User information</h2>
                {
                    loading ?
                        <Spinner/>
                        : user &&
                      <UserComponent user={user} favCount/>
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;