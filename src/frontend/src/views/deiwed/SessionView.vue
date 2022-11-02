<!-- show session details -->
<template>
    <v-card :flat="$store.getters.isMobile" class="table pb-4">
        <v-card-title>Sessão {{ session.id }}</v-card-title>
        <v-card-text>
            <!-- show date, speaker and theme of session -->
            <v-card-title class="text-center">
                <h2>Data: {{ session.date }}</h2>
            </v-card-title>
            <v-card-title class="text-center">
                <h2>Orador: {{ session.speaker }}</h2>
            </v-card-title>
            <v-card-title class="text-center">
                <h2>Tema: {{ session.theme }}</h2>
            </v-card-title>
            <!-- show image of session -->
            <v-card-title class="text-center">
                <img :src="session.image" alt="Imagem da sessão" />
            </v-card-title>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
import SessionDto from '@/models/deiwed/SessionDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class SessionView extends Vue {
    session: SessionDto[] = [];
    loading = false;
    
    async mounted() {
        this.loading = true;
        this.session = await RemoteServices.getSession(parseInt(this.$route.params.id));
        console.log(this.session);
        this.loading = false;
    }
}
</script>