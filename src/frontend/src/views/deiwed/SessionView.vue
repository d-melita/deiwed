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
            <!-- v-tabs for attendees and dishes -->
            <v-tabs v-model="tab" centered>
                <v-tab>Participantes</v-tab>
                <v-tab>Pratos</v-tab>
                <v-tab-item>
                    <v-card flat>
                        <v-card-text>
                            <v-data-table
                                :headers="headers_attendees"
                                :items="attendees"
                                :loading="loading"
                                single-select
                                locale="pt-PT"
                                no-data-text="Não existem participantes registados"
                                no-results-text="Nenhum participante corresponde aos critérios indicados"
                                sort-by="name"
                            >
                                <template v-slot:[`item.id`]="{ item }">
                                    <v-btn
                                        :to="`/attendees/${item.id}`"
                                        color="primary"
                                        text
                                        small
                                        >{{ item.id }}</v-btn
                                    >
                                </template>
                                <template v-slot:[`item.actions`]="{ item }">
                                    <v-btn
                                        color="error"
                                        @click="removeAttendee(item)"
                                    >
                                        <v-icon>mdi-delete</v-icon>
                                    </v-btn>
                                </template>
                            </v-data-table>
                            <v-btn
                                v-if="!$store.getters.isMobile"
                                color="primary"
                                class="mx-auto"
                                :to="`/attendees/add-attendee`"
                            >
                                <v-icon left>mdi-plus</v-icon> Adicionar
                                Participante
                            </v-btn>
                        </v-card-text>
                    </v-card>
                </v-tab-item>
                <v-tab-item>
                    <v-card flat>
                        <v-card-text>
                            <v-data-table
                                :headers="headers_dishes"
                                :items="dishes"
                                :loading="loading"
                                single-select
                                locale="pt-PT"
                                no-data-text="Não existem pratos registados"
                                no-results-text="Nenhum prato corresponde aos critérios indicados"
                                sort-by="name"
                            >
                                <template v-slot:[`item.id`]="{ item }">
                                    <v-btn
                                        :to="`/dishes/${item.id}`"
                                        color="primary"
                                        text
                                        small
                                        >{{ item.id }}</v-btn
                                    >
                                </template>
                                <template v-slot:[`item.actions`]="{ item }">
                                    <v-btn
                                        color="error"
                                        @click="removeDish(item)"
                                    >
                                        <v-icon>mdi-delete</v-icon>
                                    </v-btn>
                                </template>
                            </v-data-table>
                            <v-btn
                                v-if="!$store.getters.isMobile"
                                color="primary"
                                class="mx-auto"
                                :to="`/dishes/add-dish`"
                            >
                                <v-icon left>mdi-plus</v-icon> Adicionar Prato
                            </v-btn>
                        </v-card-text>
                    </v-card>
                </v-tab-item>
            </v-tabs>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
import SessionDto from '@/models/deiwed/SessionDto';
import AttendeeDto from '@/models/deiwed/AttendeeDto';
import DishDto from '@/models/deiwed/DishDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class SessionView extends Vue {
    session: SessionDto = new SessionDto();
    attendees: AttendeeDto[] = [];
    dishes: DishDto[] = [];
    loading = false;

    // v-tabs
    tab = null;

    // attendees table
    headers_attendees = [
        { text: 'Nome', value: 'name' },
        { text: 'IST ID', value: 'istId', sortable: true, filterable: true },
        { text: 'Tipo', value: 'type', sortable: true, filterable: false },
        { text: 'Ações', value: 'actions', sortable: false },
    ];

    // dishes table
    headers_dishes = [
        { text: 'Nome', value: 'name', sortable: true, filterable: true },
        { text: 'Preço', value: 'unitPrice', sortable: true, filterable: true },
        { text: 'Descrição', value: 'description', sortable: true, filterable: true },
        { text: 'Ações', value: 'actions', sortable: false },
    ];

    async mounted() {
        this.loading = true;
        this.session = await RemoteServices.getSession(parseInt(this.$route.params.id));
        this.loading = false;
    }
}
</script>